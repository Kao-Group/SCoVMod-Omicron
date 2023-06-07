/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scovmod.model.transition.beta;

import scovmod.model.state.infection.InfectionState;

public interface BetaRate {
    public double getBeta(InfectionState state, int location);
}
