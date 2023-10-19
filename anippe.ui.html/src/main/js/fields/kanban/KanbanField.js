import {FormField, ObjectFactory} from '@eclipse-scout/core';

export default class KanbanField extends FormField {

	constructor() {
		super();
	}

	_render() {
		console.log('kanban init');

		this.addContainer(this.$parent, 'gantt-field');
		this.addLabel();
		this.addStatus();
		var fieldId = ObjectFactory.get().createUniqueId();
		var $field = this.$container
			.makeDiv('gantt')
			.attr('id', 'kanban');
		this.addField($field);


		var kanban1 = new jKanban({
			element: '#kanban',
			responsivePercentage: true,
			boards: [
				{
					'id': '_todo',
					'title': 'Try Drag me!',
					'item': [
						{
							'title': 'You can drag me too'
						},
						{
							'title': 'Buy Milk'
						}
					]
				},
				{
					'id': '_working',
					'title': 'Working',
					'item': [
						{
							'id':'55',
							'title': 'Do Something!'
						},
						{
							'title': 'Run?'
						}
					]
				},
				{
					'id': '_done',
					'title': 'Done',
					'item': [
						{
							'title': 'All right'
						},
						{
							'title': 'Ok!'
						}
					]
				}
			],
			click: function(el) {
				console.log(el)
			},                             // callback when any board's item are clicked
			context: function(el, event) {
			},                      // callback when any board's item are right clicked
			dragEl: function(el, source) {
			},                     // callback when any board's item are dragged
			dragendEl: function(el) {
				console.log($(el).data())
			},                             // callback when any board's item stop drag
			dropEl: function(el, target, source, sibling) {
			},    // callback when any board's item drop in a board
			dragBoard: function(el, source) {
			},                     // callback when any board stop drag
			dragendBoard: function(el) {
			},                             // callback when any board stop drag
			buttonClick: function(el, boardId) {
				console.log(el, boardId)
			}
		});

	}

}
