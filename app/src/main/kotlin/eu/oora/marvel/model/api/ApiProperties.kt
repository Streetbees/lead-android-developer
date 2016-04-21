package eu.oora.marvel.model.api

import eu.oora.marvel.MainApplication
import java.util.Properties

class ApiProperties(application: MainApplication) {
  val publicKey: String
  val privateKey: String

  init {
    val assets = application.assets
    val stream = assets.open("api.properties")

    val properties = Properties()
    properties.load(stream)

    publicKey = properties.getProperty("apiPublicKey")
    privateKey = properties.getProperty("apiPrivateKey")
  }
}