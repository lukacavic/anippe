import { FormFieldAdapter } from '@eclipse-scout/core';

export default class GanttFieldAdapter extends FormFieldAdapter {

	constructor() {
		super();
	}

	_onWidgetEvent(event) {
		if (event.type === 'onItemClick') {
			this._onWidgetItemClick(event);
		} else {
			super._onWidgetEvent(event);
		}
	}

	_onWidgetItemClick(event) {
		this._send('itemClicked', {
			itemId: event.id
		});
	}

}
