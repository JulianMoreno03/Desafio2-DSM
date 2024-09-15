package edu.udb.desafio2dsm.models

import android.os.Parcel
import android.os.Parcelable

data class Comidas(
    val id: Int = 0,
    val nombre: String = "",
    val precio: Double = 0.0,
    var cantidad: Int = 1,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeDouble(precio)
        parcel.writeInt(cantidad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comidas> {
        override fun createFromParcel(parcel: Parcel): Comidas {
            return Comidas(parcel)
        }

        override fun newArray(size: Int): Array<Comidas?> {
            return arrayOfNulls(size)
        }
    }
}

private fun Parcel.writeInt(cantidad: String?) {

}
