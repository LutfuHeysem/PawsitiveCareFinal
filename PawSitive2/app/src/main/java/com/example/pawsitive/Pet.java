package com.example.pawsitive;

public class Pet {
    private String name, type, energyLevel, feedingCondition, numberOfWalks, notes;
    private int age, weight;
    private boolean neutered, microchipped, friendly, canBeLeftAlone, houseTrained;
    private byte[] imageInBase64;

    public Pet(String name, String type, String energyLevel, String feedingCondition, String numberOfWalks,
               String notes, int age, int weight, boolean neutered, boolean microchipped, boolean friendly, boolean canBeLeftAlone,
               boolean houseTrained, byte[] imageInBase64){

        this.name = name;
        this.type = type;
        this.energyLevel = energyLevel;
        this.feedingCondition = feedingCondition;
        this.numberOfWalks = numberOfWalks;
        this.notes = notes;
        this.age = age;
        this.weight = weight;
        this.neutered = neutered;
        this.microchipped = microchipped;
        this.friendly = friendly;
        this.canBeLeftAlone = canBeLeftAlone;
        this.houseTrained = houseTrained;
        this.imageInBase64 = imageInBase64;

    }

    public byte[] getImageInBase64() {
        return imageInBase64;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getEnergyLevel() {
        return energyLevel;
    }

    public String getFeedingCondition() {
        return feedingCondition;
    }

    public String getNumberOfWalks() {
        return numberOfWalks;
    }

    public String getNotes() {
        return notes;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isNeutered() {
        return neutered;
    }

    public boolean isMicrochipped() {
        return microchipped;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isCanBeLeftAlone() {
        return canBeLeftAlone;
    }

    public boolean isHouseTrained() {
        return houseTrained;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEnergyLevel(String energyLevel) {
        this.energyLevel = energyLevel;
    }

    public void setFeedingCondition(String feedingCondition) {
        this.feedingCondition = feedingCondition;
    }

    public void setNumberOfWalks(String numberOfWalks) {
        this.numberOfWalks = numberOfWalks;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    public void setMicrochipped(boolean microchipped) {
        this.microchipped = microchipped;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public void setCanBeLeftAlone(boolean canBeLeftAlone) {
        this.canBeLeftAlone = canBeLeftAlone;
    }

    public void setHouseTrained(boolean houseTrained) {
        this.houseTrained = houseTrained;
    }

    public void setImageInBase64(byte[] imageInBase64) {
        this.imageInBase64 = imageInBase64;
    }
}
