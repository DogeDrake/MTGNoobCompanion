import android.os.Parcel
import android.os.Parcelable

data class Paso(
    val ID: Int,
    val Nombre: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ID)
        parcel.writeString(Nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Paso> {
        override fun createFromParcel(parcel: Parcel): Paso {
            return Paso(parcel)
        }

        override fun newArray(size: Int): Array<Paso?> {
            return arrayOfNulls(size)
        }
    }
}
