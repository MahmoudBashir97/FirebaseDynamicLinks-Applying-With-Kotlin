package com.mahmoud_bashir.firebasedynamiclinks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.mahmoud_bashir.firebasedynamiclinks.chain_repository.Chain
import com.mahmoud_bashir.firebasedynamiclinks.chain_repository.DeepLink
import com.mahmoud_bashir.firebasedynamiclinks.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()  {

    private  val TAG = "MainActivity"
    lateinit var bd:ActivityMainBinding
    lateinit var chain:Chain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)

        //initialize Chain.class
        chain = Chain()

        createDeepLinkToDisplay()
        receivingDeepLinkWithHandling()

    }

    private fun receivingDeepLinkWithHandling() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(
                this
            ) { pendingDynamicLinkData ->
                // todo here we are handling received link by getting required data parameters from deepLink ....
                if (pendingDynamicLinkData != null) {
                    val deepLink: Uri?= pendingDynamicLinkData.link

                    chain.process(DeepLink(deepLink.toString()))
                    chain.process(DeepLink(deepLink.toString()))

                }

            }
            .addOnFailureListener(
                this
            ) { e -> Log.w(TAG, "getDynamicLink:onFailure", e) }
    }

    private fun createDeepLinkToDisplay() {
        // Creating a deep link and display it in the UI
        val newDeepLink = buildDeepLink(Uri.parse(DEEP_LINK_URL))
        bd.linkViewSend.text = newDeepLink.toString()

        // Share button click listener
        bd.buttonShare.setOnClickListener {  shareDeepLink(newDeepLink.toString())  }

    }

    private fun buildDeepLink(deepLink: Uri): Uri {
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(deepLink.toString()))
            .setDomainUriPrefix("https://fcmtopics.page.link")
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .setIosParameters(DynamicLink.IosParameters.Builder("com.example.ios").build())
            .buildDynamicLink()

        return dynamicLink.uri;
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