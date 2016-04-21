package eu.oora.marvel.model.api.model

class ComicsRestModel {
  val id: String
  val digitalId: String
  val title: String
  val issueNumber: String
  val variantDescription: String
  val description: String?
  val modified: String
  val isbn: String
  val upc: String
  val diamondCode: String
  val ean: String
  val issn: String
  val format: String
  val pageCount: String
  val textObjects: List<TextObjectRestModel>
  val resourceURI: String
  val urls: List<UrlRestModel>
  val series: Series
  val variants: List<Any>
  val collections: List<Any>
  val collectedIssues: List<Any>
  val dates: List<Date>
  val prices: List<PriceRestModel>
  val thumbnail: Image
  val images: List<Image>
  val creators: Any
  val characters: Any
  val stories: Any
  val events: Any

  constructor(id: String, digitalId: String, title: String, issueNumber: String, variantDescription: String, description: String, modified: String, isbn: String, upc: String, diamondCode: String, ean: String, issn: String, format: String, pageCount: String, textObjects: List<TextObjectRestModel>, resourceURI: String, urls: List<UrlRestModel>, series: Series, variants: List<Any>, collections: List<Any>, collectedIssues: List<Any>, dates: List<Date>, prices: List<PriceRestModel>, thumbnail: Image, images: List<Image>, creators: List<Any>, characters: List<Any>, stories: List<Any>, events: List<Any>) {
    this.id = id
    this.digitalId = digitalId
    this.title = title
    this.issueNumber = issueNumber
    this.variantDescription = variantDescription
    this.description = description
    this.modified = modified
    this.isbn = isbn
    this.upc = upc
    this.diamondCode = diamondCode
    this.ean = ean
    this.issn = issn
    this.format = format
    this.pageCount = pageCount
    this.textObjects = textObjects
    this.resourceURI = resourceURI
    this.urls = urls
    this.series = series
    this.variants = variants
    this.collections = collections
    this.collectedIssues = collectedIssues
    this.dates = dates
    this.prices = prices
    this.thumbnail = thumbnail
    this.images = images
    this.creators = creators
    this.characters = characters
    this.stories = stories
    this.events = events
  }

  class Series(val resourceURI: String, val name: String)

  class Date(val type: String, val date: String)

  class Image(val path: String, val extension: String)
}