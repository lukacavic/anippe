import { BasicField } from '@eclipse-scout/core';

export default class ColorField extends BasicField {

	constructor() {
		super();
		this.color = "#ffffff";
	}

	_init(model) {
		super._init(model);
	}

	_render() {
		this.addContainer(this.$parent, 'colorfield');
		this.addLabel();
		let $field = this.$parent.makeElement('<input>', 'color-field')
			.attr('type', 'color')
			.attr('value', this.color)
			.on('change', this._onColorChange.bind(this))
			.disableSpellcheck();
			
		this.addField($field);
		this.addMandatoryIndicator();
		this.addStatus();
	}

	_onColorChange(e) {
		this.trigger('changeColor', {
			color: e.currentTarget.value
		});
	}


}