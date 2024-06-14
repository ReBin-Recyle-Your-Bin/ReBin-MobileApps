package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchCraftResponse(

	@field:SerializedName("listItems")
	val listItems: List<ListItemsItem> = emptyList() ,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("currentPage")
	val currentPage: Int
)

@Parcelize
data class ListItemsItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: String? = null,

	@field:SerializedName("className")
	val className: String? = null,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("customID")
	val customID: String,

	@field:SerializedName("steps")
	val steps: String? = null
): Parcelable

