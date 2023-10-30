import {dates, FormFieldAdapter} from '@eclipse-scout/core';

export default class GanttFieldAdapter extends FormFieldAdapter {

	constructor() {
		super();
	}

	_onWidgetEvent(event) {
		if (event.type === 'onItemClick') {
			this._onWidgetItemClick(event);
		} else if (event.type === 'onItemDragged') {
			this._onWidgetItemDragged(event);
		} else {
			super._onWidgetEvent(event);
		}
	}

	_onWidgetItemClick(event) {
		this._send('itemClicked', {
			itemId: event.id
		});
	}

	_onWidgetItemDragged(event) {
		this._send('itemDragged', {
			itemId: event.id,
			startAt: dates.toJsonDate(event.startAt, false, true),
			endAt: dates.toJsonDate(event.endAt, false, true)
		});
	}

}
