import { FormFieldLayout, graphics, HtmlComponent } from '@eclipse-scout/core';

export default class TextEditorFieldLayout extends FormFieldLayout {

	constructor(stringField) {
		super(stringField);
		this.field = stringField;
	}


}
