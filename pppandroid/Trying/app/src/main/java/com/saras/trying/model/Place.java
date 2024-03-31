package com.saras.trying.model;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private String placeName;
    private String location;
    private String description;
    private String openingDays;
    private String openingHours;
    private String entryFees;
    private ContactInfo contactInfo;
    private Facilities facilities;
    private AdditionalInfo additionalInfo;
    private List<String> images;


    public Place(String placeName) {
        this.setPlaceName(placeName);
    }

    public Place() {
        this.contactInfo = new ContactInfo();
        this.facilities = new Facilities();
        this.additionalInfo = new AdditionalInfo();
        this.images = new ArrayList<>();
    }

    // Getters and Setters
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpeningDays() {
        return openingDays;
    }

    public void setOpeningDays(String openingDays) {
        this.openingDays = openingDays;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getEntryFees() {
        return entryFees;
    }

    public void setEntryFees(String entryFees) {
        this.entryFees = entryFees;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Facilities getFacilities() {
        return facilities;
    }

    public void setFacilities(Facilities facilities) {
        this.facilities = facilities;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    // Nested classes
    public static class ContactInfo {
        private String phoneNumber;
        private String email;
        private String website;

        // Getters and Setters
        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }

    public static class Facilities {
        private boolean restrooms;
        private boolean parking;
        private boolean wheelchairAccessible;
        private boolean guidedTours;
        private boolean souvenirShop;
        private boolean cafeteria;

        // Getters and Setters
        public boolean isRestrooms() {
            return restrooms;
        }

        public void setRestrooms(boolean restrooms) {
            this.restrooms = restrooms;
        }

        public boolean isParking() {
            return parking;
        }

        public void setParking(boolean parking) {
            this.parking = parking;
        }

        public boolean isWheelchairAccessible() {
            return wheelchairAccessible;
        }

        public void setWheelchairAccessible(boolean wheelchairAccessible) {
            this.wheelchairAccessible = wheelchairAccessible;
        }

        public boolean isGuidedTours() {
            return guidedTours;
        }

        public void setGuidedTours(boolean guidedTours) {
            this.guidedTours = guidedTours;
        }

        public boolean isSouvenirShop() {
            return souvenirShop;
        }

        public void setSouvenirShop(boolean souvenirShop) {
            this.souvenirShop = souvenirShop;
        }

        public boolean isCafeteria() {
            return cafeteria;
        }

        public void setCafeteria(boolean cafeteria) {
            this.cafeteria = cafeteria;
        }
    }

    public static class AdditionalInfo {
        private String nearbyAttractions;
        private String visitorTips;
        private String restrictions;

        // Getters and Setters
        public String getNearbyAttractions() {
            return nearbyAttractions;
        }

        public void setNearbyAttractions(String nearbyAttractions) {
            this.nearbyAttractions = nearbyAttractions;
        }

        public String getVisitorTips() {
            return visitorTips;
        }

        public void setVisitorTips(String visitorTips) {
            this.visitorTips = visitorTips;
        }

        public String getRestrictions() {
            return restrictions;
        }

        public void setRestrictions(String restrictions) {
            this.restrictions = restrictions;
        }
    }
}
