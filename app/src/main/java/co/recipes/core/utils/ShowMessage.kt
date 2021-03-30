package co.recipes.core.utils

import android.content.Context
import android.widget.Toast

fun mssg(context: Context, mssg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, mssg, duration).show()
}