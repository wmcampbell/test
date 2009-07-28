/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.project;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.gephi.project.api.Project;
import org.gephi.project.api.Workspace;

/**
 *
 * @author Mathieu Bastian
 */
public class WorkspaceImpl implements Workspace {

    public enum Status {

        OPEN, CLOSED, INVALID
    };
    private static int count = 0;
    private Project project;
    private String name;
    private Status status = Status.CLOSED;

    //Lookup
    private transient List<ChangeListener> listeners = new ArrayList<ChangeListener>();
    ;

    public WorkspaceImpl() {
        this("Workspace " + (count++));
    }

    public WorkspaceImpl(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
        fireChangeEvent();
    }

    public void open() {
        this.status = Status.OPEN;
        fireChangeEvent();
    }

    public void close() {
        this.status = Status.CLOSED;
        fireChangeEvent();
    }

    public void invalid() {
        this.status = Status.INVALID;
    }

    public boolean isOpen() {
        return status == Status.OPEN;
    }

    public boolean isClosed() {
        return status == Status.CLOSED;
    }

    public boolean isInvalid() {
        return status == Status.INVALID;
    }

    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    public void fireChangeEvent() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);
        }
    }
}