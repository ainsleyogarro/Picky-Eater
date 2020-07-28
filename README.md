# Picky Eater

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
[Description of your app]

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Dining
- **Mobile:** Allows for profile picture to be updated
- **Story:** Interactive experience that helps picky 
- **Market:** Can be used by those with very specific food palets
- **Habit:** Can be used every time someone will go out and eat
- **Scope:** /can be expanded from just a app about restaraunts to having a whole social aspect to it

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can login or register using parse back end
* User can search for restaraunts (Yelp API)
* User can add their favorite restaraunts to their list
* User can see list of favorite restaurants
* User has a profile picture on theri profile view
* User can see their friends and their friend's list
* Registration for new users
* Double tap to add restaurant
* Uses algorithm for combining lists with friends list
* Fade in restaurant detail view
* Can take restaurant from friend list and put it on their own
* Restaurant has detail view
* Setup bottom button navigation for list view , search view, profile, and friends view



**Optional Nice-to-have Stories**

* If store has option to user can go to website to order
* Restaraunt does not appear if past closing hours
* Allows for custom picture of restaraunt to be uploaded
* Add a party function (for groups larger than 2)
* (Real Stretch) Offline availability
* Materials Design
* User can delete account
* User can pick their favorite restaurant that shows on their profile
* User can pick between fusion, combination, exclusion


### 2. Screen Archetypes

* Login Screen
   * User can login or choose to register
* List Screen
    * User can see the list of their restaraunts
    * User can consolidate list with other friend list
* Friends Screen
    * User has lists of friends profile 
    * User can add friend by username
    
* Search Screen 
    * User can search restaraunts
* Restaraunt Screen
    * displays detail of restaraunts and allows to be added to list
    * Also will have link if online order is allowed
* Profile Screen
    * User can see pictures and have link to lists


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Friend Screen
* Restaraunt List
* Search Screen
* Profile Screen 

**Flow Navigation** (Screen to Screen)

* Login
   * List Restaraunt
* Search Screen
    * Restaraunt Screen
* Restaraunt Screen
    * Restaraunt List Screen
* Friend List
    * Their Restaraunt List Screen
* Profile Screen
    * Combined Restaraunt List Screen


## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="Wireframe.jpeg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
    Restaraunt
        Address (String)
        Hours (???)
    User
        Friends (Arrays of User)
        Restaraunts (Array of Restaraunts)
        Picture (File)
        
### Networking
- Login/Register
    - Get/Post user
- Search Screen
    - Get (Yelp Search API)
- Friend List
    - Get user
- Yelp Search API
    - Use to find List of Restaraunts
- Restaraunt View
    - Allows Restaraunt to get Posted to Users List
    - Uses Yelp to get details
- Profile Screen
    - More user gets
