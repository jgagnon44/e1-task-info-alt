package com.fossfloors.e1tasks.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.beans.TaskFilter;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

@CssImport("./styles/shared-styles.css")
@SuppressWarnings("serial")
public class TopLevelTaskView extends VerticalLayout {

  private static final Logger logger = LoggerFactory.getLogger(TopLevelTaskView.class);

  private TextField           filterField;

  private Grid<TaskMaster>    grid;

  private Binder<TaskFilter>  binder = new Binder<>(TaskFilter.class);

  private TaskMasterService   taskService;

  public TopLevelTaskView(TaskMasterService taskService) {
    this.taskService = taskService;

    setSizeFull();
    addClassName("top-level-task-grid");

    configureView();
  }

  @Override
  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
      ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

  public void loadTasks() {
    grid.setItems(taskService.getTopLevelTasks());
  }

  private void configureView() {
    filterField = new TextField("Filter");
    filterField.setClearButtonVisible(true);

    grid = new Grid<>(TaskMaster.class);

    grid.setColumns("name");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));

    grid.asSingleSelect().addValueChangeListener(event -> {
      fireEvent(new TaskSelectionEvent(this, event.getValue()));
    });

    add(filterField, grid);

    // TODO implement filtering
    binder.setBean(new TaskFilter());
    binder.forField(filterField).bind("value");
    binder.addValueChangeListener(event -> {
      // fireEvent(new FilterChangedEvent(this, binder.getBean()));
      // grid.setItems(items);
    });
  }

  public static class TaskSelectionEvent extends ComponentEvent<TopLevelTaskView> {
    private TaskMaster selected;

    public TaskSelectionEvent(TopLevelTaskView source, TaskMaster selected) {
      super(source, false);
      this.selected = selected;
    }

    public TaskMaster getSelected() {
      return selected;
    }
  }

  // TODO implement filtering
  // public static abstract class TaskFilterEvent extends ComponentEvent<TopLevelTaskView> {
  // private TaskFilter filterSpec;
  //
  // protected TaskFilterEvent(TopLevelTaskView source, TaskFilter filterSpec) {
  // super(source, false);
  // this.filterSpec = filterSpec;
  // }
  //
  // public TaskFilter getFilterSpec() {
  // return filterSpec;
  // }
  // }
  //
  // public static class FilterChangedEvent extends TaskFilterEvent {
  // public FilterChangedEvent(TopLevelTaskView source, TaskFilter filterSpec) {
  // super(source, filterSpec);
  // }
  // }

}
