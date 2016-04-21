package eu.oora.marvel.model.api.response

import eu.oora.marvel.model.api.model.ComicsRestModel

class GetComicsListResponse {
  val code: Int
  val status: String
  val copyright: String
  val attributionText: String
  val attributionHTML: String
  val etag: String
  val data: GetComicsListResult

  constructor(code: Int, status: String, copyright: String, attributionText: String, attributionHTML: String, etag: String, data: GetComicsListResult) {
    this.code = code
    this.status = status
    this.copyright = copyright
    this.attributionText = attributionText
    this.attributionHTML = attributionHTML
    this.etag = etag
    this.data = data
  }

  class GetComicsListResult {
    val offset: Int
    val limit: Int
    val total: Int
    val count: Int
    val results: List<ComicsRestModel>

    constructor(offset: Int, limit: Int, total: Int, count: Int, results: List<ComicsRestModel>) {
      this.offset = offset
      this.limit = limit
      this.total = total
      this.count = count
      this.results = results
    }
  }
}
