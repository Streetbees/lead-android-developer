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

	
### Version

- This is as much I could do in a short timeframe, the app is not nearly perfect
- The actions are the following:
	- On item long click you can open the camera to change the picture, if you are not logged in with your dropbox account it will ask for that first
	- On item click, it switches the images between the custom one and the original one
	- I also added a search mechanism for already loaded comics
	
- Issues:
	- The worker fragment is not handled completely, it may have some bugs like not dismissing
	- The pairing between the comic and the custom cover is not handled properly, I did something quick because of my time constrains (It should have been a database there)
	- A preload should be in place, to have everything loaded when the comic screen is displayed 
	- The search mechanism doesn't act like a filter if you are loading more comics 
	- ... ... ... ... ...