import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VulWebAppBlogModule } from './blog/blog.module';
import { VulWebAppEntryModule } from './entry/entry.module';
import { VulWebAppTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VulWebAppBlogModule,
        VulWebAppEntryModule,
        VulWebAppTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VulWebAppEntityModule {}
