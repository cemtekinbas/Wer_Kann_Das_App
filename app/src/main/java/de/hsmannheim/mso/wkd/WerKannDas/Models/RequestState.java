package de.hsmannheim.mso.wkd.WerKannDas.Models;

public enum RequestState {
    OPEN(1), CLOSED(2), FULFILLED(3), TRASH(4);

    private int id;

    RequestState(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }

    public static RequestState getById(int i){
        for(RequestState r : values()){
            if( r.id == i){
                return r;
            }
        }
        return null;
    }
}
