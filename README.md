###### API Docs :- https://documenter.getpostman.com/view/24098668/2s8YCjBr9o

# How it works?

## [Bot](https://twitter.com/catktorbot)
- When the endpoint of the bot gets triggered by the cron-job, the [postNewTweet()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/bot/twitter/PostNewTweet.kt#L15) function in [PostNewTweet.kt](https://github.com/sakethpathike/catktorbot/blob/master/src/main/kotlin/com/sakethh/bot/twitter/PostNewTweet.kt) will trigger the Twitter endpoint through the [Twitter4J library](https://github.com/Twitter4J/Twitter4J), which will post the data that is fetched from [fetchRandomPost()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/redditData/FetchRandomPost.kt#L16).
- [fetchRandomPost()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/redditData/FetchRandomPost.kt#L16) retrieves random data from a randomly selected subreddit.
- The same data that is sent to Twitter via bot will be added to a database using [addNewPostToDB()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/AddNewPostToDB.kt#L8), which you can retrieve through the API.

#### In short:

![botWorking](https://user-images.githubusercontent.com/83284398/198224398-7302d09d-3a44-4d63-84aa-00077bc45ce7.png)

## [API](https://catktorapi.onrender.com/)
- When the [previousPosts](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/FetchPreviousPosts.kt#L11) endpoint gets triggered, data that is posted by the bot will be retrieved from the database.
- When the [previous post with a date filter](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/FetchPreviousPosts.kt#L18) added in the parameter gets triggered, the API will return filtered data from the database.

###### API Docs :- https://documenter.getpostman.com/view/24098668/2s8YCjBr9o

# Tech Stack
- [Ktor](https://ktor.io/) - for building bot, API, and posting respective data to the database.
- [MongoDB](https://www.mongodb.com/) - for storing previous posts that are posted by the bot, which can be retrieved by the API.
- [Zeplo](https://www.zeplo.io/) - for triggering the bot endpoint automatically every third hour; In other words, zeplo is used as a cron-job(s) service(if that makes sense in this context).
- [Render](https://render.com/) - for deploying the project using [Docker](https://www.docker.com/).

# License

```
MIT License

Copyright (c) 2022 Saketh Pathike

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
