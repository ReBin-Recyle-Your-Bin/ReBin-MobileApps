package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchCraftResponse(

	@field:SerializedName("listItems")
	val listSearchCraft: List<SearchCraftItems>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
): Parcelable

@Parcelize
data class SearchCraftItems(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("ingredients")
	val ingredients: String,

	@field:SerializedName("className")
	val className: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("customID")
	val customID: String,

	@field:SerializedName("steps")
	val steps: String
):Parcelable

