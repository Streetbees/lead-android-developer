# Streetbees lead Android developer position

[Role description](https://github.com/Streetbees/lead-android-developer/wiki/Role-description)

[Requirements](https://github.com/Streetbees/lead-android-developer/wiki/Requirements)

[Benefits](https://github.com/Streetbees/lead-android-developer/wiki/Benefits)


### To apply you shoud follow the instructions below:

- Fork this repo;
- Look at the specification below and do your thing;
- When ready open a pull into the master branch of this repo and mention @streetbees/development;
- We will then review the code and if necessary discuss within the pull request.

### Challenge spec:

- Description
    - Using the best API available on this side of the universe, https://developer.marvel.com/ , make a simple app that allows the user to scroll trough all the comics ever released from the most recent to the oldest (and please, let me see the cover picture while I do it!).
    - For each entry on the list make it possible for the user to replace a cover image with his own image (taken from the phone camera) and save the custom entry to the user’s dropbox account.

- Functional requirements (Using the Job to be Done framework)

    - When i open the application I want to see a list of all Marvel’s released comic books covers ordered from most recent to the oldest so I can scroll trough the the Marvel universe.
    - When I select one of the comics I want to be able to change the cover picture with a photo taken from my camera so I can be a Marvel character!
    - When I change a comic cover image I want to able to store it in my dropbox account so I won’t lose it when I reopen the application.

- Technical requirements
    - Application must run in an Android running Ice Cream Sandwich or later.

- Evaluation Criteria
    - you create maintainable code;
    - you care about the user experience ;
    - you pay attention to details;
    - you develop in a scalable manner .

- Deliverables
    - The forked version of this repo;
    - A video of the app working (a Fabric or Hockeyapp invite from which we can directly run the app will also add a good amount of bonus points).

============================================================================================================================================================
### Known issues and improvements:
- Memory consumption increases due to massive data from API. Should allocate less objects or use objects pool to store visible objects.
- Poor implementation of taking picture with camera and replacing comic poster image. Maybe, usage of EventBus library could solve complex listener pattern.
- Overused Bitmaps. To save precious memory possibly LRU cache could be implemented to store blured bitmap objects.
- After taking picture sometimes white blank screen may appear.

(Due lack of time the task is not fully completed! SQLite layer and disk storage could be used store taken pictures. HTTP layer cache could be used on decrease requests to API, and many many many other things may be done...)