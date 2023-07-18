import {ObjectFactory} from '@eclipse-scout/core';

// export your custom JS files here. Example:
// export * from './yourFolder/YourClass';

// Define namespace and put it onto window (necessary for model variants, e.g. @ModelVariant(${classPrefixLowerCase}.Example)
import * as self from './index';


export { default as ColorField } from './fields/color/ColorField';
export { default as ColorFieldAdapter } from './fields/color/ColorFieldAdapter';

export { default as TextEditorFieldLayout } from './fields/text-editor/TextEditorFieldLayout';
export { default as TextEditorField } from './fields/text-editor/TextEditorField';
export { default as TextEditorFieldAdapter } from './fields/text-editor/TextEditorFieldAdapter';

export default self;
ObjectFactory.get().registerNamespace('anippe', self);
