package board

import board.boards.Board

class Port(portNumber: Number, board: Board) {
    var reporting = false;
    var pins = ArrayList<Pin>()
}