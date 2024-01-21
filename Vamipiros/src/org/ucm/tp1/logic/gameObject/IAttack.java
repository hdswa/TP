package org.ucm.tp1.logic.gameObject;

public interface IAttack {
	void attack();

	default boolean receiveSlayerAttack(int damage) {
			return false;
		};
	default boolean receiveVampireAttack(int damage) {
			return false;
		};
	default boolean receiveGarlicPush() {
			return false;
		};
	default boolean receiveLightFlash() {
			return false;
	};
	default boolean receiveExplosiveDMG(int damage) {
		return false;
	};
}
	
