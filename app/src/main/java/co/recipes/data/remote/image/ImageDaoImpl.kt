package co.recipes.data.remote.image

import android.util.Log
import co.recipes.core.Result
import co.recipes.core.exceptions.ImageDaoException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ImageDaoImpl(): ImageDao {

    val firebaseStorage = FirebaseStorage.getInstance().reference

    override suspend fun uploadImage(image: ByteArray, userUid: String): Result<String> = try {
        val imageRef = firebaseStorage.child("$userUid.jpg")
        imageRef.putBytes(image).await()
        var imageUrl = ""
        imageRef.downloadUrl.addOnSuccessListener { imageUrl = it.toString() }.await()
        Result.Success(imageUrl)
    }catch (e: Exception){
        throw ImageDaoException("Algo salio mal al cargar la imagen")
    }
}