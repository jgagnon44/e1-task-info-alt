package com.fossfloors.e1tasks.ui;

import java.util.List;

import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.service.MasterMenuService;
import com.fossfloors.e1tasks.util.MasterMenuDataProvider;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;

@Route("master-menus")
@CssImport("./styles/shared-styles.css")
public class MasterMenuView extends VerticalLayout {

  private static final long       serialVersionUID = 1L;

  private final MasterMenuService service;

  private ComboBox<String>        viewCombo;

  private TreeGrid<Task>          taskGrid;
  private MasterMenuDataProvider  provider;

  public MasterMenuView(MasterMenuService service) {
    this.service = service;

    setSizeFull();
    addClassName("master-menus-view");

    configureView();
  }

  private void configureView() {
    configViewSelector();
    configTreeGridLayout();
    add(viewCombo, taskGrid);
  }

  private void configViewSelector() {
    List<String> taskViews = service.getMasterMenuTaskViewIDs();

    viewCombo = new ComboBox<>("Select a Task View");
    viewCombo.setItems(taskViews);

    viewCombo.addValueChangeListener(event -> {
      provider.setTaskView(event.getValue());
      provider.refreshAll();
    });
  }

  private void configTreeGridLayout() {
    taskGrid = new TreeGrid<>();
    taskGrid.addHierarchyColumn(Task::getName).setHeader("Name");
    taskGrid.addColumn(Task::getType).setHeader("Type");

    taskGrid.getColumns().forEach(col -> {
      col.setAutoWidth(true);
      col.setResizable(true);
    });

    provider = new MasterMenuDataProvider(service);
    taskGrid.setDataProvider(provider);

    taskGrid.asSingleSelect().addValueChangeListener(event -> {
      fireEvent(new TaskSelectionEvent(taskGrid, event.getValue()));
    });
  }

  public static class TaskSelectionEvent extends ComponentEvent<TreeGrid<?>> {
    private static final long serialVersionUID = 1L;

    private Task              selected;

    public TaskSelectionEvent(TreeGrid<?> source, Task selected) {
      super(source, false);
      this.selected = selected;
    }

    public Task getSelected() {
      return selected;
    }
  }

}
