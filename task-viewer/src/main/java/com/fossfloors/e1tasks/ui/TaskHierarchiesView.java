package com.fossfloors.e1tasks.ui;

import com.fossfloors.e1tasks.backend.service.TaskRelationshipService;
import com.fossfloors.e1tasks.backend.service.TasksService;
import com.fossfloors.e1tasks.backend.service.VariantDetailService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Deprecated
@Route("tree")
@CssImport("./styles/shared-styles.css")
public class TaskHierarchiesView extends VerticalLayout {

  private static final long             serialVersionUID = 1L;

  private TaskTreeView                  taskTreeView;
  private TaskDetailView                taskDetailView;

  private ComboBox<String>              variantCombo;

  private final TasksService            tasksService;

  // private final TaskMasterService taskService;
  private final TaskRelationshipService relationService;
  private final VariantDetailService    variantService;

  public TaskHierarchiesView(TasksService tasksService, TaskRelationshipService relationService,
      VariantDetailService variantService) {
    this.tasksService = tasksService;
    this.relationService = relationService;
    this.variantService = variantService;

    setSizeFull();
    addClassName("task-hierarchies-view");

    configureView();
  }

  private void configureView() {
    configVariantSelector();
    add(configInfoPane(), variantCombo, configTreeGridLayout());

    this.addListener(VariantSelectedEvent.class, taskTreeView::setTaskVariantName);
    taskTreeView.addListener(TaskTreeView.TaskSelectionEvent.class, taskDetailView::loadTask);
  }

  private Component configInfoPane() {
    HorizontalLayout layout = new HorizontalLayout();

    TextField pt = new TextField("# parent tasks");
    pt.setReadOnly(true);
    pt.setValue(String.valueOf(relationService.getParentTaskIDs().size()));

    TextField dpt = new TextField("# distinct parent tasks");
    dpt.setReadOnly(true);
    dpt.setValue(String.valueOf(relationService.getDistinctParentTaskIDs().size()));

    TextField ct = new TextField("# child tasks");
    ct.setReadOnly(true);
    ct.setValue(String.valueOf(relationService.getChildTaskIDs().size()));

    TextField dct = new TextField("# distinct child tasks");
    dct.setReadOnly(true);
    dct.setValue(String.valueOf(relationService.getDistinctChildTaskIDs().size()));

    TextField tlt = new TextField("# top-level tasks");
    tlt.setReadOnly(true);
    tlt.setValue(String.valueOf(relationService.getTopLevelParentTaskIDs().size()));

    layout.add(pt, dpt, ct, dct, tlt);
    return layout;
  }

  private void configVariantSelector() {
    variantCombo = new ComboBox<>("Task Variant Selector");
    variantCombo.setItems(variantService.getDistinctVariantNames());

    variantCombo.addValueChangeListener(event -> {
      fireEvent(new VariantSelectedEvent(variantCombo, event.getValue()));
    });
  }

  private Component configTreeGridLayout() {
    SplitLayout layout = new SplitLayout();
    layout.setOrientation(Orientation.VERTICAL);
    layout.setSizeFull();

    taskTreeView = new TaskTreeView(tasksService);
    taskTreeView.setHeight("70%");

    taskDetailView = new TaskDetailView();
    taskDetailView.setHeight("30%");

    layout.addToPrimary(taskTreeView);
    layout.addToSecondary(taskDetailView);
    return layout;
  }

  public static class VariantSelectedEvent extends ComponentEvent<ComboBox<?>> {
    private static final long serialVersionUID = 1L;

    private String            selected;

    public VariantSelectedEvent(ComboBox<?> source, String selected) {
      super(source, false);
      this.selected = selected;
    }

    public String getSelected() {
      return selected;
    }
  }

}
