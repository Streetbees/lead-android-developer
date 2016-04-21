package eu.oora.marvel.model.model

class ComicBookModel {
  val id: String
  val title: String
  val issueNumber: String
  val description: String
  val isbn: String
  val format: String
  val pageCount: String
  val prices: List<PriceModel>
  val thumbnail: String
  val images: List<String>

  constructor(id: String, title: String, issueNumber: String, description: String, isbn: String, format: String, pageCount: String, prices: List<PriceModel>, thumbnail: String, images: List<String>) {
    this.id = id
    this.title = title
    this.issueNumber = issueNumber
    this.description = description
    this.isbn = isbn
    this.format = format
    this.pageCount = pageCount
    this.prices = prices
    this.thumbnail = thumbnail
    this.images = images
  }

  override fun toString(): String {
    return "ComicBookModel(id='$id', title='$title', issueNumber='$issueNumber', description='$description', isbn='$isbn', format='$format', pageCount='$pageCount', prices=$prices, thumbnail='$thumbnail', images=$images)"
  }
}
