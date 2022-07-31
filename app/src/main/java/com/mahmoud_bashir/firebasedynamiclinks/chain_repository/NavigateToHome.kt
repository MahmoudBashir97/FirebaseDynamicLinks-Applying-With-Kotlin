package com.mahmoud_bashir.firebasedynamiclinks.chain_repository

class NavigateToHome (nextProcessor:ProcessorLink): ProcessorLink(nextProcessor)
{
    override fun process(deepLink: DeepLink?){
        if (deepLink != null && !deepLink.deepUrl.contains("preorder")) {
            println("ToHomeScreen : " + deepLink.deepUrl.toString())
        } else {
            super.process(deepLink)
        }
    }
}