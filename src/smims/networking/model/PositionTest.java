package smims.networking.model;

import static org.junit.Assert.*;

import org.junit.Test;

import smims.networking.model.Position.StartingPositionBuilder;

public class PositionTest {

	@Test
	public void test() {
		final StartingPositionBuilder spb = Position.on(new BoardDescriptor(1,10)).startingAt(0);
		
		final Position expected = spb.atHousePosition(0);
		final Position actual = spb.atPosition(9).movedBy(1).get();
		
		assertEquals(expected, actual);
	}

}
