package com.bangkit.rebinmobileapps.data.response

import com.google.gson.annotations.SerializedName

data class CraftPagingResponse(

	@field:SerializedName("listItems")
	val listItemCraftPaging: List<SearchCraftItems>,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("currentPage")
	val currentPage: Int
)
