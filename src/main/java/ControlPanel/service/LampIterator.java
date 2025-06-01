package ControlPanel.service;

import ControlPanel.model.Lamp;
import java.util.Iterator;

//специализированный итератор для ламп
public interface LampIterator extends Iterator<Lamp> {
    boolean hasNext();
    Lamp next();
}