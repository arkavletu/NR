package ru.netology.myrecipes

interface RecipeActionListener {
//        fun onLikeClicked(post: Post)
//        fun onShareClicked(post: Post)
        fun onFabClicked()
        fun onDeleteClicked(recipe: Recipe)
        fun onEditClicked(recipe: Recipe)
//        fun onPlayClicked(post: Post)
//        fun onPostClicked(id:Long)

}