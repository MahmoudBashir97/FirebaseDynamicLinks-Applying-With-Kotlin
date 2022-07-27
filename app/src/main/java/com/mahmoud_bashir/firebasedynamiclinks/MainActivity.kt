package com.mahmoud_bashir.firebasedynamiclinks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.mahmoud_bashir.firebasedynamiclinks.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    lateinit var bd:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)

        // Creating a deep link and display it in the UI
        val newDeepLink = buildDeepLink(Uri.parse(DEEP_LINK_URL))
        bd.linkViewSend.text = newDeepLink.toString()

        // Share button click listener
        bd.buttonShare.setOnClickListener {  shareDeepLink(newDeepLink.toString())  }

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(
                this
            ) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                val deepLink: Uri?
                // todo here we are handling received link by getting required data parameters from deepLink ....
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    // todo u have to do check it it contains on id and type
                    if (deepLink.toString().contains("id") && deepLink.toString().contains("type") ) {
                        val id = deepLink.toString().split("id=")[1].split("?")[0]
                        val type = deepLink.toString().split("type=")[1]

                        Toast.makeText(this@MainActivity,"received received Id : $id , type : $type" ,Toast.LENGTH_LONG).show()

                    }

                }

                // Handle the deep link. For example, open the linked content,
                // or apply promotional credit to the user's account.
                // ...

                // ...
            }
            .addOnFailureListener(
                this
            ) { e -> Log.w(TAG, "getDynamicLink:onFailure", e) }
    }

    fun buildDeepLink(deepLink: Uri): Uri {
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(deepLink.toString()))
            .setDomainUriPrefix("https://fcmtopics.page.link")
            // Open links with this app on Android
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            // Open links with com.example.ios on iOS
            .setIosParameters(DynamicLink.IosParameters.Builder("com.example.ios").build())
            .buildDynamicLink()

        val dynamicLinkUri = dynamicLink.uri

        return dynamicLinkUri;
    }

    private fun shareDeepLink(deepLink: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase Deep Link")
        intent.putExtra(Intent.EXTRA_TEXT, deepLink)

        startActivity(intent)
    }

    companion object {

        private const val TAG = "MainActivity"
        private const val DEEP_LINK_URL = "https://www.b4eer.com/id=10?type=preorder"
    }

}