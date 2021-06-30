package com.example.kontakta

class Model2(
    var estadoUser: String,
    var sexUser: String,
    var municipioUser: String,
    var edadUser: Int,
    var nombrePrestador: String,
    val imagenPrestador: String,
    var IDServicio_FK: String
) {
}
class Model3(
    var estadoUser: String,
    var sexUser: String,
    var municipioUser: String,
    var edadUser: Int
)
class Model4(
    var edadUser: Int,
    var sexUser: String,
    var estadoUser: String,
    var municipioUser: String,
    var nombrePrestador: String,
    var IDServicio_FK: String,
    val imagenPrestador: String,
    var pNormalProb: DoubleArray = DoubleArray(2),
    var nClass: Int
)