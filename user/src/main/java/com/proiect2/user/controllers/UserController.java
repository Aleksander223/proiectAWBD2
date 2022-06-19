package com.proiect2.user.controllers;


import com.proiect2.user.model.User;
import com.proiect2.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/user/list", produces = {"application/hal+json"})
    public CollectionModel<User> findAll() {
        List<User> users = userService.findAll();
        for (final User user : users) {
            Link selfLink = linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel();
            user.add(selfLink);
            Link deleteLink = linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("deleteUser");
            user.add(deleteLink);
        }
        Link link = linkTo(methodOn(UserController.class).findAll()).withSelfRel();
        CollectionModel<User> result = CollectionModel.of(users, link);
        return result;
    }

    @PostMapping("/user")
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}").buildAndExpand(savedUser.getId())
                .toUri();


        return ResponseEntity.created(locationUri).body(savedUser);
    }


    @Operation(summary = "delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @DeleteMapping("/user/{userId}")
    public User deleteUser(@PathVariable Long userId) {

        User user = userService.delete(userId);
        return user;
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Long userId) {

        User user = userService.findById(userId);
        return user;

    }
}

