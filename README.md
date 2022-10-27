###### API Docs :- https://documenter.getpostman.com/view/24098668/2s8YCjBr9o

# How it works?

## Bot
- When the endpoint of the bot gets triggered by the cron-job, the [postNewTweet()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/bot/twitter/PostNewTweet.kt#L15) function in [PostNewTweet.kt](https://github.com/sakethpathike/catktorbot/blob/master/src/main/kotlin/com/sakethh/bot/twitter/PostNewTweet.kt) will trigger the Twitter endpoint through the [Twitter4J library](https://github.com/Twitter4J/Twitter4J), which will post the data that is fetched from [fetchRandomPost()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/redditData/FetchRandomPost.kt#L16).
- [fetchRandomPost()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/redditData/FetchRandomPost.kt#L16) retrieves random data from a randomly selected subreddit.
- The same data that is sent to Twitter via bot will be added to a database using [addNewPostToDB()](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/AddNewPostToDB.kt#L8), which you can retrieve through the API.

#### In short:

![botWorking](https://user-images.githubusercontent.com/83284398/198224398-7302d09d-3a44-4d63-84aa-00077bc45ce7.png)

## API
- When the [previousPosts](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/FetchPreviousPosts.kt#L11) endpoint gets triggered, data that is posted by the bot will be retrieved from the database.
- When the [previous post with a date filter](https://github.com/sakethpathike/catktorbot/blob/a67bf082fd52c87083b628fa7809350a881dfdc1/src/main/kotlin/com/sakethh/api/FetchPreviousPosts.kt#L18) added in the parameter gets triggered, the API will return filtered data from the database.
