import { BasicFieldAdapter } from '@eclipse-scout/core';

export default class TextEditorFieldAdapter extends BasicFieldAdapter {

	constructor() {
		super();
	}

	_onWidgetEvent(event) {
		super._onWidgetEvent(event);
	 }

	onModelAction(event) {
		if (event.type === 'insertContentBetween') {
			this._insertContentBetween(event);
		} else {
			super.onModelAction(event);
		}
	}
	
	_insertContentBetween(event) {
		this.widget.insertContentBetween(event.content);
	}
}