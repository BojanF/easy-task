package com.easytask.model.jpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Bojan on 7/31/2017.
 */

@Embeddable
public class CoworkerId implements Serializable {

    @Column(name = "userA_id")
    private Long userA;

    @Column(name = "userB_id")
    private Long userB;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CoworkerId that = (CoworkerId) o;
        return Objects.equals(userA, that.userA) &&
                Objects.equals(userB, that.userB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userA, userB);
    }

    private CoworkerId(){}

    public CoworkerId(Long userA, Long userB){
        this.userA = userA;
        this.userB = userB;
    }

}
