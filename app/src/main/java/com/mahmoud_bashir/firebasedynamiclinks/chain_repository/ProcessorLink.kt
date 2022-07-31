package com.mahmoud_bashir.firebasedynamiclinks.chain_repository

abstract class ProcessorLink(private val nextProcess: ProcessorLink?) {

    open fun process(deepLink: DeepLink?) {
        if(deepLink != null) nextProcess?.process(deepLink)
    }

}