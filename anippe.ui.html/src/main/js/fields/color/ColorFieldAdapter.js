import { BasicFieldAdapter } from '@eclipse-scout/core';

export default class ColorFieldAdapter extends BasicFieldAdapter {

	constructor() {
		super();
	}

	_onWidgetEvent(event) {
		if (event.type === 'changeColor') {
			this._onWidgetChangeColor(event);
		} else {
			super._onWidgetEvent(event);
		}
	}

	_onWidgetChangeColor(event) {
		this._send('changeColor', {
			color: event.color
		})
	};

}