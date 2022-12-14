###### API Docs :- https://documenter.getpostman.com/view/24098668/2s8YCjBr9o

# How it works?

## [Bot](https://twitter.com/catktorbot)
#### This is what the visual of the bot looks like:
![botWorking.png](https://cdn.hashnode.com/res/hashnode/image/upload/v1666944932122/U6vq-EQ5Z.png)
- When the endpoint of the bot gets triggered by the cron-job, the [postNewTweet()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/bot/twitter/PostNewTweet.kt#L15) function in [PostNewTweet.kt](https://github.com/sakethpathike/catktorbot/blob/master/src/main/kotlin/com/sakethh/bot/twitter/PostNewTweet.kt) will trigger the Twitter endpoint through the [Twitter4J library](https://github.com/Twitter4J/Twitter4J), which will post the data that is fetched from [fetchRandomPost()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/redditData/FetchRandomPost.kt#L16).
- [fetchRandomPost()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/redditData/FetchRandomPost.kt#L16) retrieves random data from a randomly selected subreddit.
- The same data that is sent to Twitter via bot will be added to a database using [addNewPostToDB()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/AddNewPostToDB.kt#L8), which you can retrieve through the API.

## [API](https://catktorapi.onrender.com/)
#### This is what the visual of the API looks like:
![apiWorking.png](https://cdn.hashnode.com/res/hashnode/image/upload/v1666945007797/WC_g_ZxtG.png)
- When the [previousPosts](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/FetchPreviousPosts.kt#L11) endpoint gets triggered, data that is posted by the bot will be retrieved from the database.
- When the [previous post with a date filter](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/FetchPreviousPosts.kt#L18) added in the parameter gets triggered, the API will return filtered data from the database.

###### API Docs :- https://documenter.getpostman.com/view/24098668/2s8YCjBr9o

# Tech Stack
- [Ktor](https://ktor.io/) - for building the bot, API, and posting respective data to the database.
- [Ktor-Client](https://ktor.io/docs/getting-started-ktor-client.html) - for retrieving JSON data from subreddits(s).
- [MongoDB](https://www.mongodb.com/) - for storing previous posts that are posted by the bot, which can be retrieved by the API.
- [KMongo](https://github.com/Litote/kmongo) - for chit-chatting with MongoDB.
- [Twitter4j](https://github.com/Twitter4J/Twitter4J) - for interacting with the Twitter API so that the bot can post the data to Twitter.
- [Apache Commons IO](https://commons.apache.org/proper/commons-io/) - converts the URL into a file (so that the bot can tweet the image URL as an image that was retrieved from the subreddit).
- [Zeplo](https://www.zeplo.io/) - for triggering the bot endpoint automatically every third hour; In other words, zeplo is used as a cron-job(s) service(if that makes sense in this context).
- [Ktor Plugins:ktor-api-key](https://github.com/LukasForst/ktor-plugins) - It is a simple authentication provider for Ktor that verifies the presence of the API key from the header.
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
