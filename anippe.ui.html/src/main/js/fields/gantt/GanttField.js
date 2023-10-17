import {FormField, ObjectFactory} from '@eclipse-scout/core';

export default class GanttField extends FormField {

	constructor() {
		super();

		this.gantt = null;
	}

	_render() {

		this.addContainer(this.$parent, 'gantt-field');
		this.addLabel();
		this.addStatus();
		var fieldId = ObjectFactory.get().createUniqueId();
		var $field = this.$container
			.makeDiv('gantt')
			.attr('id', 'gantt-field');
		this.addField($field);

		this.gantt = new Gantt('.gantt-field', this.items, {
			height:'100%',
			on_click: function (task) {
				console.log(task);
			},
			on_date_change: function(task, start, end) {
				console.log(task, start, end);
			},
			on_progress_change: function(task, progress) {
				console.log(task, progress);
			},
			on_view_change: function(mode) {
				console.log(mode);
			}
		});

	}

}
