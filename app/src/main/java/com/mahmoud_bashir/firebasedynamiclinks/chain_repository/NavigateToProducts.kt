package com.mahmoud_bashir.firebasedynamiclinks.chain_repository

class NavigateToProducts(nextProcessor: ProcessorLink?): ProcessorLink(nextProcessor)
{
     override fun process(deepLink: DeepLink?){
        if (deepLink != null && deepLink.deepUrl.contains("type")) {
            println("ToProductsScreen : " + deepLink.deepUrl.toString())
        } else {
            super.process(deepLink)
        }
    }
}