package com.example.pawsitive.classes;

public class MyPetModel {
    String imageStr = "", name = "", type = "", friendly = "", neutered = "", microchipped = "", food = "", walks = "", trained;
    double age = (double) 0, weight = (double) 0;

    public MyPetModel(String imageStr, String name, String type, String friendly, String neutered, String microchipped, String food, String walks, Double age, Double weight, String trained) {
        this.imageStr = imageStr;
        this.name = name;
        this.type = type;
        this.friendly = friendly;
        this.neutered = neutered;
        this.microchipped = microchipped;
        this.food = food;
        this.walks = walks;
        this.age = age;
        this.weight = weight;
        this.trained = trained;
    }

    public void setTrained(String trained) {
        this.trained = trained;
    }

    public String getTrained() {
        return trained;
    }

    public String getImageStr() {
        return imageStr;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFriendly() {
        return friendly;
    }

    public String getNeutered() {
        return neutered;
    }

    public String getMicrochipped() {
        return microchipped;
    }

    public String getFood() {
        return food;
    }

    public String getWalks() {
        return walks;
    }

    public int getAge() {
        return (int)age;
    }

    public int getWeight() {
        return (int)weight;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFriendly(String friendly) {
        this.friendly = friendly;
    }

    public void setNeutered(String neutered) {
        this.neutered = neutered;
    }

    public void setMicrochipped(String microchipped) {
        this.microchipped = microchipped;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setWalks(String walks) {
        this.walks = walks;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
