package dao.punk.objects

import dao.punk.Punk

class BedObject(x: Float, y: Float, punk: Punk) : BaseObject("bed.png", x, y, punk, { punk.satisfySleep(10) })