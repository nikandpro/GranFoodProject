package com.github.nikandpro;

import com.github.nikandpro.requestWeb.controller.*;
import com.github.nikandpro.tools.Service;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.enableDevLogging();
            javalinConfig.enableCorsForAllOrigins();
            javalinConfig.defaultContentType = "application/json";
        }).start(7123);

        System.out.println("check main");
        app.post("user/post" , ctx -> UserController.createUser(ctx));
        app.get("user/get" , ctx -> UserController.getAllUser(ctx));
        app.get("user/get/:id" , ctx -> UserController.getUser(ctx));
        app.patch("user/patch" , ctx -> UserController.updateUser(ctx));
        app.delete("user/delete" , ctx -> UserController.deleteUser(ctx));

        app.post("food/post" , ctx -> FoodController.createFood(ctx));
        app.get("food/get" , ctx -> FoodController.getAllFood(ctx));
        app.get("food/get/:id" , ctx -> FoodController.getFood(ctx));
//        app.get("food_subtag/get", ctx -> Service.findFoodSubtag(ctx));
//        app.patch("food/patch/:id" , ctx -> FoodController.updateFood(ctx));
        app.delete("food/delete/:id" , ctx -> FoodController.deleteFood(ctx));

        app.post("tag/post" , ctx -> TagController.createTag(ctx));
        app.get("tag/get" , ctx -> TagController.getTagName(ctx));
        app.get("tag/get/:id" , ctx -> TagController.getTagID(ctx));
        app.patch("tag/delete/:id" , ctx -> TagController.deleteTag(ctx));

        app.post("subtag/post" , ctx -> SubTagController.createTag(ctx));
        app.get("subtag/get" , ctx -> SubTagController.getTagName(ctx));
        app.get("subtag/get/:id" , ctx -> SubTagController.getTagID(ctx));
        app.patch("subtag/delete/:id" , ctx -> SubTagController.deleteTag(ctx));

        app.post("reviews/post" , ctx -> ReviewsController.postReviews(ctx));
        app.get("reviews/get" , ctx -> ReviewsController.getReviewsAll(ctx));
        app.get("reviews/get/:id" , ctx -> ReviewsController.getReviewsId(ctx));
        app.patch("reviews/patch/:id" , ctx -> ReviewsController.updateReviews(ctx));
        app.delete("reviews/delete" , ctx -> ReviewsController.deleteReviews(ctx));

    }
}
