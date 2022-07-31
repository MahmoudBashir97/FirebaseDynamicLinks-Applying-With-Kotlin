package com.mahmoud_bashir.firebasedynamiclinks.chain_repository

class Chain {
    var chain: ProcessorLink? = null
    private fun buildChain() {
        chain = NavigateToHome(NavigateToProducts(EndDestination(null)))
    }

    fun process(request: DeepLink?) {
        chain?.process(request)
    }

    init {
        buildChain()
    }
}