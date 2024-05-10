# Android Example Application

This is an example Android Application README to show briefly the sections your app README should contain.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone git@github.com:Mahmoud20-sudo/OrangeTask.git
```

## Configuration
### Keystores:
Create `app/keystore` with the following info:
```gradle
ext.key_alias='key'
ext.key_password='OrangeTask'
ext.store_password='OrangeTask'

## About

Orange task a local list of movies that should be displayed in any order. The list is searchable and the search results will be categorized by Year.
Each search result category will hold at most the top rated 5 movies of this category (year).
Once a movie is selected from the search results, you will switch to a detailed view to unveil the following:
  ● Movie Title
  ● Movie Year
  ● Movie Genres (if any)
  ● Movie Cast (if any)
  ● A two column list of pictures fetched from flickr that matches the movie title as the
  search query

## Features

The android app lets you:
  ● See a list of movies 
  ● Search for any top rated movie catgeorized by year
  ● Switching between list and details screens
  ● Use Flicker to retrieve photos related to a movie 
  ● Needs no special permissions on Android 6.0+.

## Screenshots

<img width="383" alt="Screenshot 2024-05-10 at 7 03 47 AM" src="https://github.com/Mahmoud20-sudo/OrangeTask/assets/62750799/4b276e63-2515-48a1-809a-50586a8a877c">
<img width="385" alt="Screenshot 2024-05-10 at 7 03 59 AM" src="https://github.com/Mahmoud20-sudo/OrangeTask/assets/62750799/2745d56c-71a5-47c7-87bc-b969030cc4f2">
<img width="385" alt="Screenshot 2024-05-10 at 7 04 14 AM" src="https://github.com/Mahmoud20-sudo/OrangeTask/assets/62750799/3d7e11e4-0911-4ea7-8267-0f891fbda1d7">


## Generating signed APK
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*

## Maintainers
This project is mantained by:
* [Mahmoud Mohamed](https://github.com/Mahmoud20-sudo)

## Permissions

On Android versions prior to Android 6.0, wallabag requires the following permissions:
- Full Network Access.


## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Run the linter (ruby lint.rb').
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request
