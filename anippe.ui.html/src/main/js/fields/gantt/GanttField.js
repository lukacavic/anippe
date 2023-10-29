import {FormField, ObjectFactory} from '@eclipse-scout/core';

export default class GanttField extends FormField {

	constructor() {
		super();

		this.gantt = null;
	}

	_renderViewMode() {
		this.gantt.change_view_mode(this.viewMode);
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
			header_height: 50,
			column_width: 100,
			step: 24,
			view_modes: ['Quarter Day', 'Half Day', 'Day', 'Week', 'Month'],
			bar_height: 25,
			bar_corner_radius: 3,
			arrow_curve: 5,
			padding: 18,
			view_mode: this.viewMode,
			date_format: 'YYYY-MM-DD',
			language: 'en', // or 'es', 'it', 'ru', 'ptBr', 'fr', 'tr', 'zh', 'de', 'hu'
			custom_popup_html: null,
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
