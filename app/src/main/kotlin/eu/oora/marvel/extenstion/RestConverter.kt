package eu.oora.marvel.extenstion

import eu.oora.marvel.model.api.model.ComicsRestModel
import eu.oora.marvel.model.api.model.PriceRestModel
import eu.oora.marvel.model.model.ComicBookModel
import eu.oora.marvel.model.model.PriceModel
import java.util.ArrayList

fun PriceRestModel.toModel(): PriceModel {
  return PriceModel(type, price)
}

fun ComicsRestModel.toModel(): ComicBookModel {
  val thumbnail = "${thumbnail.path}.${thumbnail.extension}"

  val images = ArrayList<String>()
  for (image in this.images) {
    images.add("${image.path}.${image.extension}")
  }

  val prices = ArrayList<PriceModel>()
  for (price in this.prices) {
    prices.add(price.toModel())
  }

  return ComicBookModel(id, title, issueNumber, description.orEmpty(), isbn, prices, thumbnail, images)
}